import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Search, ChevronRight } from 'lucide-react';
import { Header } from '@/components/layout/Header';
import { BottomNav } from '@/components/layout/BottomNav';
import { SocietyCard } from '@/components/cards/SocietyCard';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { fetchSocieties } from '@/lib/api';
import { Society } from '@/types';

const Index = () => {
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');
  const [societies, setSocieties] = useState<Society[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let isActive = true;
    setIsLoading(true);
    fetchSocieties()
      .then((data) => {
        if (!isActive) return;
        setSocieties(data);
        setError(null);
      })
      .catch((err: Error) => {
        if (!isActive) return;
        setError(err.message);
      })
      .finally(() => {
        if (!isActive) return;
        setIsLoading(false);
      });

    return () => {
      isActive = false;
    };
  }, []);

  const filteredSocieties = societies.filter(society =>
    society.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
    society.area.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const topRated = [...societies].sort((a, b) => b.averageRating - a.averageRating).slice(0, 3);

  return (
    <div className="min-h-screen bg-background pb-20">
      <Header />
      
      <main className="max-w-lg mx-auto px-4 py-6">
        {/* Search Bar */}
        <div className="relative mb-6">
          <Search className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
          <Input
            type="text"
            placeholder="Search societies, areas..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="pl-12"
          />
        </div>

        {/* Hero Banner */}
        <section className="relative overflow-hidden rounded-2xl gradient-primary p-6 mb-8 animate-fade-in">
          <div className="relative z-10">
            <h2 className="font-serif text-2xl font-bold text-primary-foreground mb-2">
              Find Your Perfect Home
            </h2>
            <p className="text-primary-foreground/80 text-sm mb-4">
              Read honest reviews from verified residents before you move in
            </p>
            <Button
              variant="secondary"
              size="sm"
              onClick={() => navigate('/search')}
              className="gap-1"
            >
              Explore Now
              <ChevronRight className="w-4 h-4" />
            </Button>
          </div>
          <div className="absolute -right-8 -bottom-8 w-32 h-32 bg-primary-foreground/10 rounded-full" />
          <div className="absolute right-8 bottom-16 w-16 h-16 bg-primary-foreground/10 rounded-full" />
        </section>

        {/* Top Rated Section */}
        <section className="mb-8">
          <div className="flex items-center justify-between mb-4">
            <h2 className="font-serif text-xl font-semibold">Top Rated</h2>
            <Button variant="ghost" size="sm" className="text-primary gap-1">
              View All
              <ChevronRight className="w-4 h-4" />
            </Button>
          </div>
          {isLoading ? (
            <p className="text-sm text-muted-foreground">Loading top-rated societies...</p>
          ) : error ? (
            <p className="text-sm text-destructive">Unable to load societies right now.</p>
          ) : (
            <div className="flex gap-4 overflow-x-auto pb-2 -mx-4 px-4 scrollbar-hide">
              {topRated.map((society, index) => (
                <SocietyCard
                  key={society.id}
                  society={society}
                  onClick={() => navigate(`/society/${society.id}`)}
                  className="min-w-[280px] flex-shrink-0"
                  style={{ animationDelay: `${index * 100}ms` }}
                />
              ))}
            </div>
          )}
        </section>

        {/* Near You Section */}
        <section>
          <div className="flex items-center justify-between mb-4">
            <h2 className="font-serif text-xl font-semibold">Near You</h2>
            <span className="text-sm text-muted-foreground">
              {filteredSocieties.length} societies
            </span>
          </div>
          {isLoading ? (
            <p className="text-sm text-muted-foreground">Loading nearby societies...</p>
          ) : error ? (
            <p className="text-sm text-destructive">Unable to load societies right now.</p>
          ) : (
            <div className="space-y-4">
              {filteredSocieties.map((society, index) => (
                <SocietyCard
                  key={society.id}
                  society={society}
                  onClick={() => navigate(`/society/${society.id}`)}
                  style={{ animationDelay: `${index * 50}ms` }}
                />
              ))}
            </div>
          )}
        </section>
      </main>

      <BottomNav />
    </div>
  );
};

export default Index;
