import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { CommandeFournisseurService } from '../service/commande-fournisseur.service';

import { CommandeFournisseurComponent } from './commande-fournisseur.component';

describe('CommandeFournisseur Management Component', () => {
  let comp: CommandeFournisseurComponent;
  let fixture: ComponentFixture<CommandeFournisseurComponent>;
  let service: CommandeFournisseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [CommandeFournisseurComponent],
    })
      .overrideTemplate(CommandeFournisseurComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CommandeFournisseurComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CommandeFournisseurService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.commandeFournisseurs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
